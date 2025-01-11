import { Component } from '@angular/core';
import { PagetitleService } from '../../services/pagetitle.service';

@Component({
  selector: 'app-trips',
  templateUrl: './trips.component.html',
  styleUrl: './trips.component.scss',
})
export class TripsComponent {
  constructor(private pageTitle: PagetitleService) {}

  ngOnInit() {
    this.pageTitle.title.next('Manage trips');
  }
}
