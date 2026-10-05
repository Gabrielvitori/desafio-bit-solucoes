import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { SolicitacaoList } from './pages/solicitacao-list/solicitacao-list';
import { SolicitacaoForm } from './pages/solicitacao-form/solicitacao-form';

export const routes: Routes = [
  // Se o usuário acessar a raiz (localhost:4200), joga direto para o login
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'dashboard', component: Dashboard },
  { path: 'solicitacoes', component: SolicitacaoList },
  { path: 'solicitacoes/nova', component: SolicitacaoForm },
  { path: 'solicitacoes/:id', component: SolicitacaoForm },

  // Rota curinga: Se digitar uma URL que não existe, joga pro login
  { path: '**', redirectTo: 'login' }
];
