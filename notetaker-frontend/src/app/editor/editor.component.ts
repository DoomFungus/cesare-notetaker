import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import * as ClassicEditor from 'cesare-notetaker-editor-build';
import {EditorService} from "./editor.service";
import {Attachment} from "../shared/attachment";

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.sass']
})
export class EditorComponent implements OnInit, OnChanges{

  @Input()
  note_id:number
  attachments: Attachment[]
  tags

  public model = {
    editorData: ""
  };

  constructor(private editorService:EditorService) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const self = this;
    if (this.note_id !== 0){
      document.getElementById("content_loader").style.display = 'block'
      this.editorService
        .getNoteContent(this.note_id.toString())
        .subscribe(value => {
          value.text()
            .then(text => self.model.editorData = text)
          document.getElementById("content_loader").style.display = 'none'
        })
      this.editorService
        .getNote(this.note_id.toString())
        .subscribe(value => {
          self.attachments = value.attachments
        })
    }
  }

  public Editor = ClassicEditor;
  ckConfig = {
    image: {
      styles: ['full', 'alignLeft', 'alignRight'],
      toolbar: [ 'imageStyle:full', 'imageStyle:alignRight', 'imageStyle:alignLeft' ]
    }
  };

  onSave(){
    this.editorService.putNoteContent(this.note_id.toString(), this.model.editorData)
      .subscribe();
  }
}
