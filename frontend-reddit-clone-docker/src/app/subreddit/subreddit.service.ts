import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Subreddit } from './subreddit.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubredditService {
  private apiUrl: string = environment.baseUrl;
  private subredditContext = '/subreddits';

  constructor(private httpClient: HttpClient) { }

  public allSubreddits(): Observable<Array<Subreddit>> {
    return this.httpClient.get<Array<Subreddit>>(`${this.apiUrl}${this.subredditContext}`);
  }

  public createSubreddit(subreddit: Subreddit): Observable<Subreddit> {
    return this.httpClient.post<Subreddit>(`${this.apiUrl}${this.subredditContext}`, subreddit);
  }

  public getSubreddit(id: string): Observable<Subreddit> {
    return this.httpClient.get<Subreddit>(`${this.apiUrl}${this.subredditContext}/${id}`);
  }
}
