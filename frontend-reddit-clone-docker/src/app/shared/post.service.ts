import { PostRequest } from './../post/post-request.model';
import { Post } from './../home/post.model';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl: string = environment.baseUrl;
  private postContext = '/posts';
  private postByUserContext = '/posts/by-user';

  constructor(private httpClient: HttpClient) { }

  public allPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.apiUrl}${this.postContext}`);
  }

  public createPost(postRequest: PostRequest): Observable<Post> {
    return this.httpClient.post<Post>(`${this.apiUrl}${this.postContext}`, postRequest);
  }

  public getPost(id: number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.apiUrl}${this.postContext}/${id}`);
  }

  public getPostsByUser(username: string): Observable<Array<Post>> {
    return this.httpClient.get<Array<Post>>(`${this.apiUrl}${this.postByUserContext}/${username}`);
  }
}
