import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { rootRoutes } from './routes/rootRoutes';
import './index.css';

const routes = createBrowserRouter(rootRoutes);


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={routes}  />
  </StrictMode>,
);
