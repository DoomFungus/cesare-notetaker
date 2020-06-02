import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent implements OnInit {

  @Input()
  header_text: string

  @Output()
  onNoteClickEvent = new EventEmitter<[number, string]>()

  constructor() { }

  ngOnInit(): void {
  }

  onNoteClick(event: [number, string]){
    this.onNoteClickEvent.emit(event)
  }
}
