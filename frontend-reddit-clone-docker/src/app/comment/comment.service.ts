import { CommentRequest } from './comment-request.model';
import { Comment } from './../comment/comment.model';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiUrl: string = environment.baseUrl;
  private commentContext = '/comments';
  private commentByPostContext = '/comments/by-post';
  private commentByUserContext = '/comments/by-user';

  constructor(private httpClient: HttpClient) { }

  public createPost(commentRequest: CommentRequest): Observable<Comment> {
    return this.httpClient.post<Comment>(`${this.apiUrl}${this.commentContext}`, commentRequest);
  }

  public getCommentByPost(id: number): Observable<Array<Comment>> {
    return this.httpClient.get<Array<Comment>>(`${this.apiUrl}${this.commentByPostContext}/${id}`);
  }

  public getCommentByUser(username: string): Observable<Array<Comment>> {
    return this.httpClient.get<Array<Comment>>(`${this.apiUrl}${this.commentByUserContext}/${username}`);
  }
}
