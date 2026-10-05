import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  login(credenciais: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credenciais, { withCredentials: true })
      .pipe(
        tap((resposta: any) => {
          localStorage.setItem('usuarioLogado', JSON.stringify(resposta));
        })
      );
  }
}
