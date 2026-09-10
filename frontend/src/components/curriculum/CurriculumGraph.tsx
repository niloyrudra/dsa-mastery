import { ReactFlow, Node, Edge, addEdge, Connection, NodeTypes, EdgeTypes } from 'reactflow';
import { 
  Background, 
  Controls, 
  MiniMap, 
  Panel,
  useNodesState, 
  useEdgesState,
  Handle,
  Position 
} from 'reactflow';
import 'reactflow/dist/style.css';
import {
  BookOpen,
  Code2,
  Lightbulb,
  Database,
  Zap,
  ChevronDown,
  ChevronRight,
  ExternalLink,
  CheckCircle,
  Clock,
  Lock,
  PlayCircle,
  Circle,
} from 'lucide-react';
import { useMemo, useCallback, useState } from 'react';
import clsx from 'clsx';
import type { CurriculumNodeDto } from '@/types';

interface CurriculumGraphProps {
  curriculum: CurriculumNodeDto;
  onNodeClick?: (node: CurriculumNodeDto) => void;
  masteryMap?: Map<string, { status: string; score: number }>;
}

const nodeTypes: NodeTypes = {
  category: CategoryNode,
  pattern: PatternNode,
  problem: ProblemNode,
};

const edgeTypes: EdgeTypes = {
  default: CustomEdge,
};

function CategoryNode({ data, selected }: { data: any; selected: boolean }) {
  const [expanded, setExpanded] = useState(true);
  const { name, children, progress, total } = data;
  
  const handleToggle = (e: React.MouseEvent) => {
    e.stopPropagation();
    setExpanded(!expanded);
  };

  return (
    <div className={clsx('category-node', selected && 'selected')}>
      <div className="node-header" onClick={handleToggle}>
        <div className="node-title-row">
          <BookOpen className="w-5 h-5 text-blue-500" />
          <span className="font-semibold text-slate-900 dark:text-slate-100">{name}</span>
          {total > 0 && (
            <span className="text-xs text-slate-500 dark:text-slate-400 bg-slate-100 dark:bg-slate-800 px-2 py-0.5 rounded">
              {progress}/{total}
            </span>
          )}
        </div>
        <ChevronDown className={clsx('w-4 h-4 text-slate-400 transition-transform', expanded && 'rotate-180')} />
      </div>
      {expanded && (
        <Handle type="source" position={Position.Bottom} className="w-full" />
      )}
    </div>
  );
}

function PatternNode({ data, selected }: { data: any; selected: boolean }) {
  const { name, description, problems, progress, total } = data;
  
  return (
    <div className={clsx('pattern-node', selected && 'selected')}>
      <div className="node-header">
        <div className="node-title-row">
          <Zap className="w-5 h-5 text-purple-500" />
          <span className="font-semibold text-slate-900 dark:text-slate-100">{name}</span>
          {total > 0 && (
            <span className="text-xs text-slate-500 dark:text-slate-400 bg-slate-100 dark:bg-slate-800 px-2 py-0.5 rounded">
              {progress}/{total}
            </span>
          )}
        </div>
      </div>
      <p className="text-xs text-slate-500 dark:text-slate-400 mt-1 line-clamp-2">{description}</p>
      {problems && problems.length > 0 && (
        <Handle type="source" position={Position.Bottom} className="w-full" />
      )}
    </div>
  );
}

function ProblemNode({ data, selected }: { data: any; selected: boolean }) {
  const { title, difficulty, status, score, externalUrl, slug } = data;
  const mastery = data.mastery;
  
  const difficultyColor = {
    EASY: 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200',
    MEDIUM: 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200',
    HARD: 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200',
  }[difficulty] || 'bg-slate-100 text-slate-800 dark:bg-slate-700 dark:text-slate-200';

  const statusIcon = {
    MASTERED: <CheckCircle className="w-4 h-4 text-green-500" />,
    SOLVED: <CheckCircle className="w-4 h-4 text-blue-500" />,
    PRACTICING: <PlayCircle className="w-4 h-4 text-yellow-500" />,
    LEARNING: <Circle className="w-4 h-4 text-purple-500" />,
    NOT_STARTED: <Lock className="w-4 h-4 text-slate-400" />,
  }[mastery?.status || 'NOT_STARTED'];

  return (
    <div className={clsx('problem-node', selected && 'selected')}>
      <div className="node-header">
        <div className="node-title-row">
          {statusIcon}
          <span className="font-medium text-slate-900 dark:text-slate-100 truncate">{title}</span>
          <span className={clsx('px-2 py-0.5 rounded text-xs font-medium', difficultyColor)}>
            {difficulty}
          </span>
        </div>
      </div>
      {mastery && mastery.score > 0 && (
        <div className="mt-2">
          <div className="flex justify-between text-xs text-slate-500 dark:text-slate-400 mb-1">
            <span>Mastery</span>
            <span>{mastery.score}%</span>
          </div>
          <div className="h-1.5 bg-slate-200 dark:bg-slate-700 rounded-full overflow-hidden">
            <div 
              className="h-full bg-primary-500 rounded-full transition-all"
              style={{ width: `${mastery.score}%` }}
            />
          </div>
        </div>
      )}
      <Handle type="target" position={Position.Top} className="w-full" />
      <Handle type="source" position={Position.Bottom} className="w-full" />
    </div>
  );
}

function CustomEdge({ id, sourceX, sourceY, targetX, targetY, sourcePosition, targetPosition }) {
  const path = `M ${sourceX} ${sourceY} 
    C ${sourceX} ${(sourceY + targetY) / 2} 
      ${targetX} ${(sourceY + targetY) / 2} 
      ${targetX} ${targetY}`;
  
  return (
    <path 
      d={path} 
      stroke="#94a3b8" 
      strokeWidth={1.5} 
      fill="none" 
      strokeDasharray="4,4"
      style={{ opacity: 0.6 }}
    />
  );
}

export function CurriculumGraph({ 
  curriculum, 
  onNodeClick, 
  masteryMap = new Map() 
}: CurriculumGraphProps) {
  const [nodes, setNodes] = useNodesState([]);
  const [edges, setEdges] = useEdgesState([]);
  const [selectedNode, setSelectedNode] = useState<string | null>(null);

  const buildGraph = useCallback((node: CurriculumNodeDto, parentId?: string, depth = 0) => {
    const nodeId = node.id;
    const mastery = masteryMap.get(node.slug || '');
    
    let type: 'category' | 'pattern' | 'problem' = 'category';
    if (node.type === 'PATTERN') type = 'pattern';
    else if (node.type === 'PROBLEM') type = 'problem';
    else if (node.type === 'ALGORITHM') type = 'pattern';

    const progress = node.children?.reduce((sum, c) => sum + (c.progress || 0), 0) || 0;
    const total = node.children?.reduce((sum, c) => sum + (c.total || 0), 0) || 0;

    const newNode: Node = {
      id: nodeId,
      type,
      position: { x: depth * 300, y: 0 }, // Will be auto-layouted
      data: {
        name: node.name,
        description: node.description,
        title: node.title,
        difficulty: node.difficulty,
        slug: node.slug,
        externalUrl: node.externalUrl,
        problems: node.children?.filter(c => c.type === 'PROBLEM'),
        progress,
        total,
        mastery,
      },
      selected: nodeId === selectedNode,
    };

    const newNodes = [newNode];
    const newEdges: Edge[] = [];

    if (parentId) {
      newEdges.push({
        id: `edge-${parentId}-${nodeId}`,
        source: parentId,
        target: nodeId,
        type: 'default',
      });
    }

    if (node.children && node.children.length > 0) {
      node.children.forEach((child, index) => {
        const childResult = buildGraph(child, nodeId, depth + 1);
        newNodes.push(...childResult.nodes);
        newEdges.push(...childResult.edges);
      });
    }

    return { nodes: newNodes, edges: newEdges };
  }, [selectedNode, masteryMap]);

  const { nodes: builtNodes, edges: builtEdges } = useMemo(() => {
    return buildGraph(curriculum);
  }, [curriculum, buildGraph]);

  // Update nodes/edges when built graph changes
  useMemo(() => {
    setNodes(builtNodes);
    setEdges(builtEdges);
  }, [builtNodes, builtEdges, setNodes, setEdges]);

  const onConnect = useCallback((connection: Connection) => {
    setEdges((eds) => addEdge(connection, eds));
  }, [setEdges]);

  const onNodeClickHandler = useCallback((_, node: Node) => {
    setSelectedNode(node.id);
    if (onNodeClick && node.data) {
      // Find the original curriculum node
      const findNode = (n: CurriculumNodeDto): CurriculumNodeDto | null => {
        if (n.id === node.id) return n;
        if (n.children) {
          for (const child of n.children) {
            const found = findNode(child);
            if (found) return found;
          }
        }
        return null;
      };
      const original = findNode(curriculum);
      if (original) onNodeClick(original);
    }
  }, [curriculum, onNodeClick]);

  const onPaneClick = useCallback(() => {
    setSelectedNode(null);
  }, []);

  return (
    <div className="h-full w-full">
      <ReactFlow
        nodes={nodes}
        edges={edges}
        onConnect={onConnect}
        onNodeClick={onNodeClickHandler}
        onPaneClick={onPaneClick}
        nodeTypes={nodeTypes}
        edgeTypes={edgeTypes}
        fitView
        attributionPosition="bottom-left"
      >
        <Background color="#e2e8f0" gap={16} />
        <Controls />
        <MiniMap 
          nodeColor={(node) => {
            if (node.type === 'category') return '#3b82f6';
            if (node.type === 'pattern') return '#a855f7';
            return '#64748b';
          }}
        />
        <Panel position="top-right">
          <div className="p-3 bg-white dark:bg-slate-800 rounded-lg shadow-lg border border-slate-200 dark:border-slate-700 text-xs">
            <div className="flex items-center gap-2 mb-2">
              <BookOpen className="w-4 h-4 text-blue-500" /> Category
            </div>
            <div className="flex items-center gap-2 mb-2">
              <Zap className="w-4 h-4 text-purple-500" /> Pattern
            </div>
            <div className="flex items-center gap-2 mb-2">
              <Code2 className="w-4 h-4 text-slate-500" /> Problem
            </div>
            <div className="flex items-center gap-2">
              <Circle className="w-4 h-4 text-slate-400" /> Not Started
            </div>
            <div className="flex items-center gap-2">
              <Circle className="w-4 h-4 text-purple-500" /> Learning
            </div>
            <div className="flex items-center gap-2">
              <PlayCircle className="w-4 h-4 text-yellow-500" /> Practicing
            </div>
            <div className="flex items-center gap-2">
              <CheckCircle className="w-4 h-4 text-blue-500" /> Solved
            </div>
            <div className="flex items-center gap-2">
              <CheckCircle className="w-4 h-4 text-green-500" /> Mastered
            </div>
          </div>
        </Panel>
      </ReactFlow>
    </div>
  );
}