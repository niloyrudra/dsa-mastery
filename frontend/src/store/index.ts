import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import type { Language, MasteryStatus } from '../types';

interface UIState {
  sidebarOpen: boolean;
  theme: 'light' | 'dark' | 'system';
  setSidebarOpen: (open: boolean) => void;
  toggleSidebar: () => void;
  setTheme: (theme: 'light' | 'dark' | 'system') => void;
}

export const useUIStore = create<UIState>()(
  persist(
    (set) => ({
      sidebarOpen: true,
      theme: 'system',
      setSidebarOpen: (open) => set({ sidebarOpen: open }),
      toggleSidebar: () => set((state) => ({ sidebarOpen: !state.sidebarOpen })),
      setTheme: (theme) => set({ theme }),
    }),
    {
      name: 'dsa-mastery-ui',
    }
  )
);

interface EditorState {
  language: Language;
  code: string;
  setLanguage: (language: Language) => void;
  setCode: (code: string) => void;
  reset: () => void;
}

export const useEditorStore = create<EditorState>()(
  persist(
    (set) => ({
      language: 'TYPESCRIPT',
      code: '',
      setLanguage: (language) => set({ language }),
      setCode: (code) => set({ code }),
      reset: () => set({ code: '' }),
    }),
    {
      name: 'dsa-mastery-editor',
    }
  )
);

interface AITutorState {
  isOpen: boolean;
  conversationId: string | null;
  problemId: string | null;
  messages: Array<{
    id: string;
    role: 'user' | 'assistant' | 'system';
    content: string;
    type: string;
  }>;
  isStreaming: boolean;
  open: (problemId?: string) => void;
  close: () => void;
  setConversationId: (id: string) => void;
  addMessage: (message: AITutorState['messages'][0]) => void;
  updateLastMessage: (content: string) => void;
  setStreaming: (streaming: boolean) => void;
  clearMessages: () => void;
}

export const useAITutorStore = create<AITutorState>((set) => ({
  isOpen: false,
  conversationId: null,
  problemId: null,
  messages: [],
  isStreaming: false,
  open: (problemId) => set({ isOpen: true, problemId, conversationId: null, messages: [], isStreaming: false }),
  close: () => set({ isOpen: false, conversationId: null, problemId: null, messages: [], isStreaming: false }),
  setConversationId: (id) => set({ conversationId: id }),
  addMessage: (message) => set((state) => ({ messages: [...state.messages, message] })),
  updateLastMessage: (content) => set((state) => ({
    messages: state.messages.map((m, i) => i === state.messages.length - 1 ? { ...m, content } : m)
  })),
  setStreaming: (streaming) => set({ isStreaming: streaming }),
  clearMessages: () => set({ messages: [], conversationId: null }),
}));

interface MasteryState {
  problemMastery: Record<string, MasteryStatus>;
  updateProblemMastery: (problemId: string, status: MasteryStatus) => void;
  getProblemMastery: (problemId: string) => MasteryStatus;
}

export const useMasteryStore = create<MasteryState>()(
  persist(
    (set, get) => ({
      problemMastery: {},
      updateProblemMastery: (problemId, status) => set((state) => ({
        problemMastery: { ...state.problemMastery, [problemId]: status }
      })),
      getProblemMastery: (problemId) => get().problemMastery[problemId] || 'NOT_STARTED',
    }),
    {
      name: 'dsa-mastery-progress',
    }
  )
);