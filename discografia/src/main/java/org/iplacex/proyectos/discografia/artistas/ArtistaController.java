package org.iplacex.proyectos.discografia.artistas;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    private final IArtistaRepository repo;

    public ArtistaController(IArtistaRepository repo) { this.repo = repo; }

    @PostMapping(value="/artista", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista body) {
        Artista saved = repo.save(body);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping(value="/artistas", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping(value="/artista/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleGetArtistaRequest(@PathVariable String id) {
        return repo.findById(id)
                .<ResponseEntity<?>>map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND));
    }

    @PutMapping(value="/artista/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista incoming) {
        if (!repo.existsById(id)) {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
        incoming._id = id;
        return new ResponseEntity<>(repo.save(incoming), HttpStatus.OK);
    }

    @DeleteMapping(value="/artista/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (!repo.existsById(id)) {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
        repo.deleteById(id);
        return new ResponseEntity<>("{\"message\":\"Eliminado\"}", HttpStatus.OK);
    }
}
