import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = 'http://localhost:8080/api/dashboard';

  constructor(private http: HttpClient) { }

  obterResumo(): Observable<any> {
    const params = new HttpParams().set('cb', new Date().getTime().toString());

    return this.http.get<any>(this.apiUrl, {
      params: params,
      headers: { 'Cache-Control': 'no-cache, no-store, must-revalidate', 'Pragma': 'no-cache' },
      withCredentials: true
    });
  }
}
