import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SolicitacaoService } from '../../services/solicitacao';

@Component({
  selector: 'app-solicitacao-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './solicitacao-form.html',
  styleUrls: ['./solicitacao-form.css']
})
export class SolicitacaoForm implements OnInit {
  form: FormGroup;
  solicitacaoId: number | null = null;
  modoVisualizacao = false;
  isAdmin = false;
  usuarioLogadoId: number = 0;
  solicitacaoAtual: any = null;

  private apiUrl = 'http://localhost:8080/api/solicitacoes';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private solicitacaoService: SolicitacaoService
  ) {
    this.form = this.fb.group({
      titulo: ['', Validators.required],
      categoria: ['', Validators.required],
      descricao: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.verificarPerfil();

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.solicitacaoId = Number(idParam);
      this.carregarSolicitacao();
    }
  }

  verificarPerfil() {
    const dados = localStorage.getItem('usuarioLogado');
    if (dados) {
      const usuario = JSON.parse(dados);
      this.isAdmin = usuario.role === 'ROLE_ADMIN';
      this.usuarioLogadoId = usuario.id;
    }
  }

  carregarSolicitacao() {
    //  anti-cache por segurança na requisição HTTP direta
    this.http.get<any[]>(`${this.apiUrl}?cb=${Date.now()}`, { withCredentials: true }).subscribe({
      next: (lista) => {
        const encontrada = lista.find(s => s.id === this.solicitacaoId);
        if (encontrada) {
          this.solicitacaoAtual = encontrada;
          this.form.patchValue({
            titulo: encontrada.titulo,
            categoria: encontrada.categoria,
            descricao: encontrada.descricao
          });

          if (encontrada.status !== 'ABERTO' || this.isAdmin) {
            this.modoVisualizacao = true;
            this.form.disable();
          }
        }
      },
      error: () => alert('Erro ao carregar a solicitação.')
    });
  }

  salvar() {
      if (this.form.invalid) {
        alert('Preencha todos os campos obrigatórios.');
        return;
      }

      const payload = this.form.value;

      if (this.solicitacaoId) {
        this.solicitacaoService.editar(this.solicitacaoId, payload).subscribe({
          next: () => this.router.navigate(['/solicitacoes']),
          error: (err) => console.error(err)
        });
      } else {
        this.http.post(this.apiUrl, payload, { withCredentials: true }).subscribe({
          next: () => this.router.navigate(['/solicitacoes']),
          error: (err) => console.error(err)
        });
      }
    }
}
