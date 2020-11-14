import { HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { Vote } from './vote-button/vote.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VoteService {
  private apiUrl: string = environment.baseUrl;
  private voteContext = '/votes';

  constructor(private httpClient: HttpClient) { }

  vote(vote: Vote): Observable<Vote> {
    return this.httpClient.post<Vote>(`${this.apiUrl}${this.voteContext}`, vote);
  }
}
