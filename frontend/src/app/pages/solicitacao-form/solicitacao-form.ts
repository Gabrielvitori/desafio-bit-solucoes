import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

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
    private http: HttpClient
  ) {
    this.form = this.fb.group({
      titulo: ['', Validators.required],
      categoria: ['', Validators.required],
      descricao: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.verificarPerfil();

    // Verifica se a URL tem um ID (Modo Edição/Detalhes)
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
    this.http.get<any[]>(this.apiUrl, { withCredentials: true }).subscribe({
      next: (lista) => {
        const encontrada = lista.find(s => s.id === this.solicitacaoId);
        if (encontrada) {
          this.solicitacaoAtual = encontrada;
          this.form.patchValue({
            titulo: encontrada.titulo,
            categoria: encontrada.categoria,
            descricao: encontrada.descricao
          });

          // Bloqueia edição se não estiver ABERTO ou se for ADMIN (Admin não edita conteúdo, só status na listagem)
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
      //  Aviso
      alert('A edição (PUT) precisa ser mapeada no backend (SolicitacaoController).');
    } else {
      // Criar nova
      this.http.post(this.apiUrl, payload, { withCredentials: true }).subscribe({
        next: () => {
          alert('Solicitação criada com sucesso!');
          this.router.navigate(['/solicitacoes']);
        },
        error: (err) => alert('Erro ao criar solicitação.')
      });
    }
  }
}
