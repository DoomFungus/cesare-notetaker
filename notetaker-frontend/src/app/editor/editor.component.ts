import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import * as ClassicEditor from 'cesare-notetaker-editor-build';
import {EditorService} from "./editor.service";
import {Attachment} from "../shared/attachment";
import {Tag} from "../shared/tag";

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.sass']
})
export class EditorComponent implements OnInit, OnChanges{

  @Input()
  note_id:number
  attachments: Attachment[]
  tags: Tag[]

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
      Promise.all([
        this.editorService
          .getNoteContent(this.note_id.toString())
          .then(value => {
            self.model.editorData = value
          }),
        this.editorService
          .getNote(this.note_id.toString())
          .then(value => {
            self.attachments = value.attachments
            self.tags = value.tags
          })
        ])
        .then(() => document.getElementById("content_loader").style.display = 'none')
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
    document.getElementById("content_loader").style.display = 'block'
    this.editorService.putNoteContent(this.note_id.toString(), this.model.editorData)
      .then(() => document.getElementById("content_loader").style.display = 'none');
  }
}
