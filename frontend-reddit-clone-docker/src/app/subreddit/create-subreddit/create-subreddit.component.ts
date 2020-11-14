import { Router } from '@angular/router';
import { SubredditService } from './../subreddit.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subreddit } from '../subreddit.model';

@Component({
  selector: 'app-create-subreddit',
  templateUrl: './create-subreddit.component.html',
  styleUrls: ['./create-subreddit.component.css']
})
export class CreateSubredditComponent implements OnInit {
  public createSubredditForm: FormGroup;
  private createSubredditRequest: Subreddit;

  constructor(private subredditService: SubredditService, private router: Router) { }

  ngOnInit(): void {
    this.initializeLoginForm();
    this.initializeCreateSubredditRequest();
  }

  public createSubreddit() {
    this.createSubredditRequest.name = this.title.value;
    this.createSubredditRequest.description = this.description.value;
    this.subredditService.createSubreddit(this.createSubredditRequest)
          .subscribe(data => {
            this.router.navigateByUrl('/list-subreddits');
          });
  }

  public discard() {
    this.initializeLoginForm();
    this.initializeCreateSubredditRequest();
    this.router.navigateByUrl('/');
  }

  private initializeLoginForm() {
    this.createSubredditForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
  }

  private initializeCreateSubredditRequest() {
    this.createSubredditRequest = {
      name: '',
      description: ''
    };
  }

  get title() {
    return this.createSubredditForm.get('title');
  }

  get description() {
    return this.createSubredditForm.get('description');
  }
}
