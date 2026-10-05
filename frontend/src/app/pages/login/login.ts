import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {
  credenciais = { username: '', password: '' };
  mensagemErro = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  fazerLogin() {
    this.mensagemErro = '';

    this.authService.login(this.credenciais).subscribe({
      next: (resposta: any) => {
        console.log('Login realizado com sucesso!', resposta);
        this.router.navigate(['/dashboard']);
      },
      error: (erro: any) => {
        console.error('Erro no login:', erro);
        this.mensagemErro = 'Usuário ou senha incorretos.';
        this.cdr.detectChanges();
      }
    });
  }
}
