import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { SolicitacaoService } from '../../services/solicitacao';

@Component({
  selector: 'app-solicitacao-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule], // Necessário para os filtros (ngModule)
  templateUrl: './solicitacao-list.html',
  styleUrls: ['./solicitacao-list.css']
})
export class SolicitacaoList implements OnInit {
  solicitacoes: any[] = [];

  // Objeto para amarrar os campos de busca do HTML
  filtros = {
    status: '',
    categoria: '',
    titulo: '',
    dataInicio: '',
    dataFim: ''
  };

  isAdmin = false;
  usuarioLogadoId: number = 0;

  constructor(
    private solicitacaoService: SolicitacaoService,
    private router: Router
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
        this.solicitacoes = dados;
      },
      error: (err) => console.error('Erro ao carregar', err)
    });
  }

  limparFiltros() {
    this.filtros = { status: '', categoria: '', titulo: '', dataInicio: '', dataFim: '' };
    this.carregarSolicitacoes();
  }

  // AÇÕES DA TABELA
  novaSolicitacao() {
    this.router.navigate(['/solicitacoes/nova']);
  }

  verDetalhes(id: number) {
    this.router.navigate(['/solicitacoes', id]);
  }

  alterarStatus(solicitacao: any, novoStatus: string) {
    // O select do HTML disparará essa função
    this.solicitacaoService.atualizarStatus(solicitacao.id, novoStatus).subscribe({
      next: () => {
        solicitacao.status = novoStatus;
        alert('Status atualizado com sucesso!');
      },
      error: (err) => {
        alert('Erro ao atualizar status. Verifique suas permissões.');
        this.carregarSolicitacoes(); // Recarrega para voltar ao status anterior visualmente
      }
    });
  }

  excluir(id: number) {
    if (confirm('Tem certeza que deseja excluir esta solicitação?')) {
      this.solicitacaoService.excluir(id).subscribe({
        next: () => {
          this.solicitacoes = this.solicitacoes.filter(s => s.id !== id);
        },
        error: (err) => alert('Não foi possível excluir. (Apenas chamados ABERTOS podem ser excluídos pelo autor).')
      });
    }
  }
}
