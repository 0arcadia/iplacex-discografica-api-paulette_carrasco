package org.iplacex.proyectos.discografia.discos;


import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository discoRepo;
    private final IArtistaRepository artistaRepo;

    public DiscoController(IDiscoRepository discoRepo, IArtistaRepository artistaRepo) {
        this.discoRepo = discoRepo;
        this.artistaRepo = artistaRepo;
    }

    @PostMapping(value="/disco", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandlePostDiscoRequest(@RequestBody Disco body) {
        if (body == null || body.idArtista == null || body.idArtista.isBlank()) {
            return new ResponseEntity<>("idArtista es obligatorio", HttpStatus.BAD_REQUEST);
        }
        if (!artistaRepo.existsById(body.idArtista)) {
            return new ResponseEntity<>("Artista no existe", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(discoRepo.save(body), HttpStatus.CREATED);
    }

    @GetMapping(value="/discos", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return new ResponseEntity<>(discoRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping(value="/disco/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleGetDiscoRequest(@PathVariable String id) {
        return discoRepo.findById(id)
                .<ResponseEntity<?>>map(d -> new ResponseEntity<>(d, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Disco no encontrado", HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/artista/{id}/discos", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        return new ResponseEntity<>(discoRepo.findDiscosByIdArtista(id), HttpStatus.OK);
    }
}
