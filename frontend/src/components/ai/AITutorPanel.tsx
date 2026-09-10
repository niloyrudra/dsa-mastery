import { useState, useEffect, useRef } from 'react';
import { Send, RotateCcw, Copy, Lightbulb, Zap, Brain, MessageSquare, X, Check, Loader2, Sparkles } from 'lucide-react';
import { MarkdownRenderer } from '@/components/ui/MarkdownRenderer';
import clsx from 'clsx';
import type { ProblemChapterDto } from '@/types';
import { useAITutorStore } from '@/store';
import { aiApi } from '@/services/api';

const HINT_TYPES = [
  { id: 'concept', label: 'Concept', icon: Lightbulb, desc: 'Explain the core concept' },
  { id: 'hint', label: 'Hint', icon: Zap, desc: 'Get a progressive hint' },
  { id: 'approach', label: 'Approach', icon: Brain, desc: 'Discuss algorithmic approach' },
  { id: 'review', label: 'Code Review', icon: MessageSquare, desc: 'Analyze my solution' },
  { id: 'complexity', label: 'Complexity', icon: Zap, desc: 'Explain time/space complexity' },
  { id: 'variation', label: 'Variation', icon: Sparkles, desc: 'Generate problem variations' },
] as const;

export function AITutorPanel({ problem }: { problem: ProblemChapterDto }) {
  const { 
    isOpen, 
    close, 
    conversationId, 
    setConversationId, 
    messages, 
    addMessage, 
    updateLastMessage,
    setStreaming,
    clearMessages,
    problemId: aiProblemId
  } = useAITutorStore();
  
  const [input, setInput] = useState('');
  const [selectedHintType, setSelectedHintType] = useState<'concept' | 'hint' | 'approach' | 'review' | 'complexity' | 'variation'>('concept');
  const [isLoading, setIsLoading] = useState(false);
  const messagesEndRef = useRef<HTMLDivElement>(null);
  
  // Auto-open when problem changes
  useEffect(() => {
    if (problem && problem.id !== aiProblemId) {
      clearMessages();
      // Don't auto-open, let user click
    }
  }, [problem, aiProblemId, clearMessages]);
  
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };
  
  useEffect(() => {
    scrollToBottom();
  }, [messages]);
  
  const handleSendMessage = async () => {
    if (!input.trim() || isLoading) return;
    
    const userMessage = input;
    setInput('');
    addMessage({ id: `user-${Date.now()}`, role: 'user', content: userMessage, type: 'QUESTION' });
    
    // Add assistant placeholder
    const assistantId = `assistant-${Date.now()}`;
    addMessage({ id: assistantId, role: 'assistant', content: '', type: 'EXPLANATION' });
    setStreaming(true);
    setIsLoading(true);
    
    try {
      if (!conversationId) {
        const conv = await aiApi.startConversation({ problemId: problem.id, modelRole: 'CODING' });
        setConversationId(conv.data.conversationId);
      }
      
      const messageType = selectedHintType === 'review' ? 'CODE_REVIEW' : 
                          selectedHintType === 'hint' ? 'HINT' :
                          selectedHintType === 'complexity' ? 'COMPLEXITY_ANALYSIS' :
                          selectedHintType === 'variation' ? 'VARIATION' : 'EXPLANATION';
      
      const response = await aiApi.sendMessage(conversationId!, { 
        content: userMessage, 
        type: messageType 
      });
      
      // Update the assistant message with the actual response
      updateLastMessage(response.data.content);
      
    } catch (error) {
      console.error('AI error:', error);
      updateLastMessage('Sorry, I encountered an error. Please try again.');
    } finally {
      setStreaming(false);
      setIsLoading(false);
    }
  };
  
  if (!isOpen) return null;
  
  return (
    <div className="fixed inset-0 z-50 flex lg:relative lg:static">
      {/* Mobile overlay */}
      <div className="lg:hidden fixed inset-0 bg-slate-900/50" onClick={close} />
      
      {/* Panel */}
      <div className={clsx(
        'flex flex-col bg-white dark:bg-slate-900 w-full lg:w-96 border-l border-slate-200 dark:border-slate-700',
        'lg:shadow-xl'
      )}>
        {/* Header */}
        <div className="flex items-center justify-between p-4 border-b border-slate-200 dark:border-slate-700">
          <div className="flex items-center gap-3">
            <div className="p-2 bg-primary-100 dark:bg-primary-900/30 rounded-lg">
              <Sparkles className="w-5 h-5 text-primary-600 dark:text-primary-400" />
            </div>
            <div>
              <h2 className="font-semibold text-slate-900 dark:text-slate-100">AI Tutor</h2>
              <p className="text-xs text-slate-500 dark:text-slate-400">{problem.title}</p>
            </div>
          </div>
          <button onClick={close} className="p-2 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 lg:hidden">
            <X className="w-5 h-5" />
          </button>
        </div>
        
        {/* Hint Type Selector */}
        <div className="p-4 border-b border-slate-200 dark:border-slate-700">
          <label className="block text-xs font-medium text-slate-500 dark:text-slate-400 mb-2">Quick Actions</label>
          <div className="flex flex-wrap gap-2">
            {HINT_TYPES.map((hint) => (
              <button
                key={hint.id}
                onClick={() => { setSelectedHintType(hint.id as any); setInput(`Give me a ${hint.label.toLowerCase()} for this problem.`); }}
                className={clsx(
                  'px-3 py-1.5 rounded-lg text-xs font-medium transition-colors flex items-center gap-1',
                  selectedHintType === hint.id
                    ? 'bg-primary-100 text-primary-700 dark:bg-primary-900/30 dark:text-primary-300'
                    : 'bg-slate-100 text-slate-600 hover:bg-slate-200 dark:bg-slate-800 dark:text-slate-400 dark:hover:bg-slate-700'
                )}
              >
                <hint.icon className="w-3 h-3" />
                {hint.label}
              </button>
            ))}
          </div>
        </div>
        
        {/* Messages */}
        <div className="flex-1 overflow-y-auto p-4 space-y-4">
          {messages.length === 0 && (
            <div className="text-center py-12 text-slate-500 dark:text-slate-400">
              <Sparkles className="w-12 h-12 mx-auto mb-4 opacity-50" />
              <p className="font-medium">Hi! I'm your DSA tutor.</p>
              <p className="text-sm mt-1">Ask me about concepts, get hints, or have me review your code.</p>
            </div>
          )}
          
          {messages.map((msg) => (
            <div key={msg.id} className={clsx('flex gap-3', msg.role === 'user' && 'flex-row-reverse')}>
              <div className={clsx(
                'w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0',
                msg.role === 'user' ? 'bg-primary-100 dark:bg-primary-900' : 'bg-slate-100 dark:bg-slate-800'
              )}>
                {msg.role === 'user' ? (
                  <MessageSquare className="w-4 h-4 text-primary-600 dark:text-primary-400" />
                ) : (
                  <Sparkles className="w-4 h-4 text-slate-500" />
                )}
              </div>
              <div className={clsx(
                'max-w-[80%] rounded-2xl px-4 py-2',
                msg.role === 'user' 
                  ? 'bg-primary-600 text-white rounded-tr-sm' 
                  : 'bg-slate-100 dark:bg-slate-800 rounded-tl-sm'
              )}>
                {msg.role === 'assistant' && isLoading && msg.id === messages[messages.length - 1]?.id ? (
                  <div className="flex items-center gap-2">
                    <Loader2 className="w-4 h-4 animate-spin" />
                    <span>Thinking...</span>
                  </div>
                ) : (
                  <MarkdownRenderer content={msg.content} />
                )}
              </div>
            </div>
          ))}
          
          <div ref={messagesEndRef} />
        </div>
        
        {/* Input */}
        <div className="p-4 border-t border-slate-200 dark:border-slate-700">
          <div className="flex gap-2">
            <textarea
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => { if (e.key === 'Enter' && !e.shiftKey) { e.preventDefault(); handleSendMessage(); }}}
              placeholder="Ask a question or select a quick action..."
              className="flex-1 px-3 py-2 text-sm bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 rounded-lg focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-500 resize-none min-h-[44px] max-h-32"
              rows={1}
            />
            <button
              onClick={handleSendMessage}
              disabled={!input.trim() || isLoading}
              className={clsx('btn-primary p-2', !input.trim() || isLoading && 'opacity-50 cursor-not-allowed')}
            >
              {isLoading ? <Loader2 className="w-5 h-5 animate-spin" /> : <Send className="w-5 h-5" />}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}