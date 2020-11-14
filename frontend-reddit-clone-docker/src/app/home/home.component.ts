import { Post } from './post.model';
import { Component, OnInit } from '@angular/core';
import { PostService } from '../shared/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  posts: Array<Post> = [];

  constructor(private postSerive: PostService) { }

  ngOnInit(): void {
    this.postSerive.allPosts().subscribe(data => {
      this.posts = data;
    });
  }
}
