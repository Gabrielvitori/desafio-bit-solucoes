import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SolicitacaoService {
  private apiUrl = 'http://localhost:8080/api/solicitacoes';

  constructor(private http: HttpClient) {}

  listar(filtros?: any): Observable<any[]> {
      let params = new HttpParams();

      if (filtros) {
        if (filtros.status) params = params.set('status', filtros.status);
        if (filtros.categoria) params = params.set('categoria', filtros.categoria);
        if (filtros.titulo) params = params.set('titulo', filtros.titulo);

        if (filtros.dataInicio) {
          params = params.set('dataInicio', new Date(filtros.dataInicio).toISOString());
        }
        if (filtros.dataFim) {
          const fim = new Date(filtros.dataFim);
          fim.setHours(23, 59, 59, 999);
          params = params.set('dataFim', fim.toISOString());
        }
      }

      params = params.set('cb', new Date().getTime().toString());

      return this.http.get<any[]>(this.apiUrl, {
        params: params,
        headers: { 'Cache-Control': 'no-cache, no-store, must-revalidate', 'Pragma': 'no-cache' },
        withCredentials: true
      });
    }

  atualizarStatus(id: number, novoStatus: string): Observable<any> {
    return this.http.patch(`${this.apiUrl}/${id}/status`, { status: novoStatus }, { withCredentials: true });
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { withCredentials: true });
  }

  editar(id: number, dados: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, dados, { withCredentials: true });
  }
}
