import { Component } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'notetaker-frontend';
  active_note_id: number

  onNoteChosen(note_id: number){
    this.active_note_id = note_id
  }
}
