import { Component } from '@angular/core';
import * as ClassicEditor from 'ckeditor5-build-cesare-notetaker';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'notetaker-frontend';
  public Editor = ClassicEditor;
  ckConfig = {
    image: {
      styles: ['full', 'alignLeft', 'alignRight'],
      toolbar: [ 'imageStyle:full', 'imageStyle:alignRight', 'imageStyle:alignLeft' ]
    }
  };
}
