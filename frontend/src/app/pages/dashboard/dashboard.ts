import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // <-- 1. Importado o ChangeDetectorRef
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../services/dashboard';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class Dashboard implements OnInit {
  usuarioNome = 'Usuário';

  resumo = { total: 0, abertas: 0, emAtendimento: 0, concluidas: 0 };

  constructor(
    private router: Router,
    private dashboardService: DashboardService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const dadosUsuario = localStorage.getItem('usuarioLogado');
    if (dadosUsuario) {
      const usuario = JSON.parse(dadosUsuario);
      this.usuarioNome = usuario.username;
    }

    this.carregarIndicadores();
  }

  carregarIndicadores() {
    this.dashboardService.obterResumo().subscribe({
      next: (dados: any) => {
        this.resumo = dados;
        this.cdr.detectChanges();
      },
      error: (erro: any) => {
        console.error('Erro ao carregar o dashboard', erro);
      }
    });
  }

  irParaSolicitacoes() {
    this.router.navigate(['/solicitacoes']);
  }

  irParaNovaSolicitacao() {
    this.router.navigate(['/solicitacoes/nova']);
  }

  sair() {
    localStorage.removeItem('usuarioLogado');
    this.router.navigate(['/login']);
  }
}
