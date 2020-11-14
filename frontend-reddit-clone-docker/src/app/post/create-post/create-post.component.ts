import { SubredditService } from './../../subreddit/subreddit.service';
import { Subreddit } from './../../subreddit/subreddit.model';
import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/shared/post.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { PostRequest } from '../post-request.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  public createPostForm: FormGroup;
  private createPostRequest: PostRequest;
  subreddits: Array<Subreddit>;

  constructor(private postService: PostService, private router: Router,
              private subredditService: SubredditService) { }

  ngOnInit(): void {
    this.initializePostForm();
    this.initializeCreatePostRequest();
    this.subredditService.allSubreddits().subscribe(data => {
      this.subreddits = data;
    });
  }

  public createPost() {
    this.createPostRequest.description = this.description.value;
    this.createPostRequest.postName = this.postName.value;
    this.createPostRequest.subredditName = this.subredditName.value;
    this.createPostRequest.url = this.url.value;

    this.postService.createPost(this.createPostRequest).subscribe(data => {
      this.router.navigateByUrl('/');
    });
  }

  public discardPost() {
    this.router.navigateByUrl('/');
  }
  private initializePostForm() {
    this.createPostForm = new FormGroup({
      description: new FormControl('', Validators.required),
      postName: new FormControl('', Validators.required),
      subredditName: new FormControl('', Validators.required),
      url: new FormControl('')
    });
  }

  private initializeCreatePostRequest() {
    this.createPostRequest = {
      description: '',
      postName: '',
      subredditName: '',
      url: ''
    };
  }

  get description() {
    return this.createPostForm.get('description');
  }

  get postName() {
    return this.createPostForm.get('postName');
  }

  get subredditName() {
    return this.createPostForm.get('subredditName');
  }

  get url() {
    return this.createPostForm.get('url');
  }
}
