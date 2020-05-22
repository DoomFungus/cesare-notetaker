package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Notebook;
import lombok.Data;

@Data
public class NotebookInputMessage {
    @JsonProperty
    private String title;
    @JsonProperty(value = "encryption_key")
    private String encryptionKey;

    public Notebook toNotebook(){
        Notebook notebook = new Notebook();
        notebook.setTitle(this.getTitle());
        notebook.setEncryptionKey(this.encryptionKey);
        return notebook;
    }
}
