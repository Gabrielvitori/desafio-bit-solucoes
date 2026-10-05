import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { SolicitacaoService } from '../../services/solicitacao';

@Component({
  selector: 'app-solicitacao-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './solicitacao-list.html',
  styleUrls: ['./solicitacao-list.css']
})
export class SolicitacaoList implements OnInit {
  solicitacoes: any[] = [];
  filtros = { status: '', categoria: '', titulo: '', dataInicio: '', dataFim: '' };
  isAdmin = false;
  usuarioLogadoId: number = 0;

  constructor(
    private solicitacaoService: SolicitacaoService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.verificarPerfil();
    this.carregarSolicitacoes();
  }

  verificarPerfil() {
    const dados = localStorage.getItem('usuarioLogado');
    if (dados) {
      const usuario = JSON.parse(dados);
      this.isAdmin = usuario.role === 'ROLE_ADMIN';
      this.usuarioLogadoId = usuario.id;
    }
  }

  carregarSolicitacoes() {
    this.solicitacaoService.listar(this.filtros).subscribe({
      next: (dados) => {
        this.solicitacoes = [...dados];
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Erro ao carregar', err)
    });
  }

  limparFiltros() {
    this.filtros = { status: '', categoria: '', titulo: '', dataInicio: '', dataFim: '' };
    this.carregarSolicitacoes();
  }

  novaSolicitacao() {
    this.router.navigate(['/solicitacoes/nova']);
  }

  verDetalhes(id: number) {
    this.router.navigate(['/solicitacoes', id]);
  }

  alterarStatus(solicitacao: any, novoStatus: string) {
    this.solicitacaoService.atualizarStatus(solicitacao.id, novoStatus).subscribe({
      next: () => {
        solicitacao.status = novoStatus;
        alert('Status atualizado com sucesso!');
        this.cdr.detectChanges(); // Atualiza a tela após mudar o status
      },
      error: (err) => {
        alert('Erro ao atualizar status. Verifique suas permissões.');
        this.carregarSolicitacoes();
      }
    });
  }

  excluir(id: number) {
    if (confirm('Tem certeza que deseja excluir esta solicitação?')) {
      this.solicitacaoService.excluir(id).subscribe({
        next: () => {
          this.solicitacoes = this.solicitacoes.filter(s => s.id !== id);
          this.cdr.detectChanges(); // Atualiza a tela após excluir
        },
        error: (err) => alert('Não foi possível excluir. (Apenas chamados ABERTOS podem ser excluídos pelo autor).')
      });
    }
  }
}
