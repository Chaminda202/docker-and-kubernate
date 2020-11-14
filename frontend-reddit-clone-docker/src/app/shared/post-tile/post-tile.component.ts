import { Router } from '@angular/router';
import { Post } from './../../home/post.model';
import { Component, OnInit, Input } from '@angular/core';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {
  faComments = faComments;
  @Input() posts: Array<Post>;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  goToPost(id: number) {
    this.router.navigateByUrl(`/view-post/${id}`);
  }
}
