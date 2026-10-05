import { Outlet, useMatches } from 'react-router-dom';
import { TopBarProgress } from '@/routes/TopBarProgress';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { Provider } from './components/ui/provider';
import { useEffect } from 'react';
import { Toaster } from 'sonner';

const client = new QueryClient();

function App() {
  const matches = useMatches();
  const currentRoute = matches.find((m) => {
    const handle: { title?: string, key?: string } = m?.handle ?? {} as { title?: string, key?: string };

    return handle.title;
  });

  useEffect(() => {
    const handle: { title?: string, key?: string } = currentRoute?.handle ?? {} as { title?: string, key?: string };

    if (handle?.title) {
      document.title = handle.title ?? '';
    }
  }, [currentRoute]);


  return <>
    <QueryClientProvider client={client}>
      <Provider>
          <Toaster richColors/>
          <Outlet />
      </Provider>
      <TopBarProgress />
    </QueryClientProvider>
  </>;
}

export default App;
