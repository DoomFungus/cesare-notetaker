import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';

import {TagsService} from "../shared/tags.service";
import {Note} from "../shared/note";
import {Tag} from "../shared/tag";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.sass']
})
export class SearchComponent implements OnInit {

  constructor(private tagsService:TagsService, @Inject('M') private M: any) { }

  private chip;
  foundNotes: Note[]

  @Output()
  onNoteClickEvent = new EventEmitter<[number, string]>()

  ngOnInit(): void {
    const self = this
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.search-modal');
      self.M.Modal.init(elems, {});
    });
  }

  loadAutocomplete(){
    const self = this
    const elems = document.querySelectorAll('.search-chips');
    self.tagsService.getAvailableTags().then(tags => {
        self.chip = self.M.Chips.init(elems,
          {
            placeholder: "Enter tags",
            autocompleteOptions: {
              data:self.tagsToAutocompleteData(tags)
            }
          })[0];
      }
    )
  }

  private tagsToAutocompleteData(tags: Tag[]){
    let autocompleteData = {};
    tags.map(tag => autocompleteData[tag.name] = null)
    return autocompleteData
  }

  searchByTags(){
    let textData = {}
    this.chip.chipsData.map(data => textData[data.tag] = null)
    this.tagsService.getAvailableTags()
      .then(tags => tags.filter(tag => textData[tag.name] !== undefined))
      .then(tags => this.tagsService.findNotesByTags(tags.map(tag => tag.id)))
      .then(data => this.foundNotes = data)
  }

  onNoteClick(note_id:number, note_title:string){
    this.onNoteClickEvent.emit([note_id, note_title])
  }
}
