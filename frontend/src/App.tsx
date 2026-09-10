import { Routes, Route, Navigate } from 'react-router-dom';
import { QueryClientProvider } from '@tanstack/react-query';
import { queryClient } from './lib/queryClient';
import { Layout } from './components/layout/Layout';
import { CurriculumPage } from './pages/CurriculumPage';
import { ProblemPage } from './pages/ProblemPage';
import { PatternPage } from './pages/PatternPage';
import { AlgorithmPage } from './pages/AlgorithmPage';
import { DataStructurePage } from './pages/DataStructurePage';
import { TemplatePage } from './pages/TemplatePage';
import { SettingsPage } from './pages/SettingsPage';
import { MasteryDashboard } from './pages/MasteryDashboard';
import { useUIStore } from './store';

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/curriculum" replace />} />
      <Route path="/curriculum" element={<CurriculumPage />} />
      <Route path="/mastery" element={<MasteryDashboard />} />
      <Route path="/problem/:slug" element={<ProblemPage />} />
      <Route path="/pattern/:slug" element={<PatternPage />} />
      <Route path="/algorithm/:slug" element={<AlgorithmPage />} />
      <Route path="/data-structure/:slug" element={<DataStructurePage />} />
      <Route path="/template/:slug" element={<TemplatePage />} />
      <Route path="/settings" element={<SettingsPage />} />
      <Route path="*" element={<Navigate to="/curriculum" replace />} />
    </Routes>
  );
}

function App() {
  const { theme } = useUIStore();
  
  // Apply theme
  if (typeof window !== 'undefined') {
    const root = window.document.documentElement;
    root.classList.remove('light', 'dark');
    
    if (theme === 'system') {
      const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
      root.classList.add(prefersDark ? 'dark' : 'light');
    } else {
      root.classList.add(theme);
    }
  }
  
  return (
    <QueryClientProvider client={queryClient}>
      <Layout>
        <AppRoutes />
      </Layout>
    </QueryClientProvider>
  );
}

export default App;