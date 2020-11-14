import { SubredditService } from '../subreddit.service';
import { Component, OnInit } from '@angular/core';
import { Subreddit } from '../subreddit.model';

@Component({
  selector: 'app-list-subreddits',
  templateUrl: './list-subreddits.component.html',
  styleUrls: ['./list-subreddits.component.css']
})
export class ListSubredditsComponent implements OnInit {
  subreddits: Array<Subreddit>;
  constructor(private subredditService: SubredditService) { }

  ngOnInit(): void {
    this.subredditService.allSubreddits().subscribe(data => {
      this.subreddits = data;
    });
  }
}
