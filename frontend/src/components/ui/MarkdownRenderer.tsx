import ReactMarkdown from 'react-markdown';
import rehypeHighlight from 'rehype-highlight';
import { Components } from 'react-markdown';

const components: Components = {
  h1: ({ children, ...props }) => (
    <h1 {...props} className="text-2xl font-bold text-slate-900 dark:text-slate-100 mt-6 mb-3">{children}</h1>
  ),
  h2: ({ children, ...props }) => (
    <h2 {...props} className="text-xl font-semibold text-slate-900 dark:text-slate-100 mt-5 mb-2">{children}</h2>
  ),
  h3: ({ children, ...props }) => (
    <h3 {...props} className="text-lg font-medium text-slate-900 dark:text-slate-100 mt-4 mb-2">{children}</h3>
  ),
  p: ({ children, ...props }) => (
    <p {...props} className="text-slate-700 dark:text-slate-300 leading-relaxed mb-4">{children}</p>
  ),
  ul: ({ children, ...props }) => (
    <ul {...props} className="list-disc list-inside space-y-1 mb-4 text-slate-700 dark:text-slate-300">{children}</ul>
  ),
  ol: ({ children, ...props }) => (
    <ol {...props} className="list-decimal list-inside space-y-1 mb-4 text-slate-700 dark:text-slate-300">{children}</ol>
  ),
  li: ({ children, ...props }) => (
    <li {...props} className="ml-4">{children}</li>
  ),
  code: ({ children, ...props }) => {
    const isInline = !props.className;
    if (isInline) {
      return (
        <code {...props} className="bg-slate-100 dark:bg-slate-800 px-1.5 py-0.5 rounded text-sm font-mono text-primary-700 dark:text-primary-300">
          {children}
        </code>
      );
    }
    return <code {...props} className="font-mono text-sm">{children}</code>;
  },
  pre: ({ children, ...props }) => (
    <pre {...props} className="bg-slate-900 dark:bg-slate-950 rounded-lg p-4 overflow-x-auto mb-4">
      {children}
    </pre>
  ),
  blockquote: ({ children, ...props }) => (
    <blockquote {...props} className="border-l-4 border-primary-500 pl-4 italic text-slate-600 dark:text-slate-400 my-4">{children}</blockquote>
  ),
  a: ({ children, href, ...props }) => (
    <a href={href} {...props} className="text-primary-600 dark:text-primary-400 hover:underline" target="_blank" rel="noopener noreferrer">
      {children}
    </a>
  ),
  strong: ({ children, ...props }) => (
    <strong {...props} className="font-semibold text-slate-900 dark:text-slate-100">{children}</strong>
  ),
  em: ({ children, ...props }) => (
    <em {...props} className="italic">{children}</em>
  ),
  hr: ({ ...props }) => (
    <hr {...props} className="border-slate-200 dark:border-slate-700 my-6" />
  ),
  table: ({ children, ...props }) => (
    <div className="overflow-x-auto mb-4">
      <table {...props} className="min-w-full divide-y divide-slate-200 dark:divide-slate-700">{children}</table>
    </div>
  ),
  th: ({ children, ...props }) => (
    <th {...props} className="px-3 py-2 text-left text-xs font-semibold text-slate-500 dark:text-slate-400 uppercase tracking-wider bg-slate-50 dark:bg-slate-800">{children}</th>
  ),
  td: ({ children, ...props }) => (
    <td {...props} className="px-3 py-2 text-sm text-slate-700 dark:text-slate-300 border-t border-slate-200 dark:border-slate-700">{children}</td>
  ),
};

interface MarkdownRendererProps {
  content: string;
  className?: string;
}

export function MarkdownRenderer({ content, className }: MarkdownRendererProps) {
  return (
    <div className={className}>
      <ReactMarkdown components={components} rehypePlugins={[rehypeHighlight]}>
        {content}
      </ReactMarkdown>
    </div>
  );
}