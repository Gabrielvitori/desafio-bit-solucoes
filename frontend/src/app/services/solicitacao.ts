import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SolicitacaoService {
  private apiUrl = 'http://localhost:8080/api/solicitacoes';

  constructor(private http: HttpClient) {}

  // Listagem com Filtros Dinâmicos
  listar(filtros?: any): Observable<any[]> {
    let params = new HttpParams();

    if (filtros) {
      if (filtros.status) params = params.set('status', filtros.status);
      if (filtros.categoria) params = params.set('categoria', filtros.categoria);
      if (filtros.titulo) params = params.set('titulo', filtros.titulo);

      // Datas: Enviando no formato ISO que o Spring Boot espera
      if (filtros.dataInicio) {
        params = params.set('dataInicio', new Date(filtros.dataInicio).toISOString());
      }
      if (filtros.dataFim) {
        // data final
        const fim = new Date(filtros.dataFim);
        fim.setHours(23, 59, 59, 999);
        params = params.set('dataFim', fim.toISOString());
      }
    }

    return this.http.get<any[]>(this.apiUrl, { params, withCredentials: true });
  }

  // Alteração de Status (Apenas ADMIN)
  atualizarStatus(id: number, novoStatus: string): Observable<any> {
    return this.http.patch(`${this.apiUrl}/${id}/status`, { status: novoStatus }, { withCredentials: true });
  }

  //  Exclusão (Apenas o Dono ou ADMIN)
  excluir(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { withCredentials: true });
  }
}
