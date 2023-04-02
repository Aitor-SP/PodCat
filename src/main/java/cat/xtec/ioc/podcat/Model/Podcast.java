package cat.xtec.ioc.podcat.Model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Entity
@Table(name = "podcasts", catalog = "podcat")
public class Podcast {

    @ManyToOne
    @JoinColumn(name = "id_usuari")
    private Usuari usuari;

    @ManyToOne
    @JoinColumn(name = "id_canal")
    private Canal canal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_podcast")
    private Long id;

    @Column(name = "fil", nullable = false, unique = true)
    private Integer fil;

    @Column(name = "titol", nullable = false, unique = true)
    private String titol;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "etiquetes")
    private String etiquetes;

    @Column(name = "data_publicacio")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private LocalDateTime dataPublicacio;

    @Column(name = "imatge")
    private String imatge;

    @Column(name = "audio")
    private String audio;

    public Podcast() {
    }

    public Podcast(Usuari usuari, Canal canal, Long id, Integer fil, String titol, String descripcio, String etiquetes, LocalDateTime dataPublicacio, String imatge, String audio) {
        this.usuari = usuari;
        this.canal = canal;
        this.id = id;
        this.fil = fil;
        this.titol = titol;
        this.descripcio = descripcio;
        this.etiquetes = etiquetes;
        this.dataPublicacio = dataPublicacio;
        this.imatge = imatge;
        this.audio = audio;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFil() {
        return fil;
    }

    public void setFil(Integer fil) {
        this.fil = fil;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getEtiquetes() {
        return etiquetes;
    }

    public void setEtiquetes(String etiquetes) {
        this.etiquetes = etiquetes;
    }

    public LocalDateTime getDataPublicacio() {
        return dataPublicacio;
    }

    public void setDataPublicacio(LocalDateTime dataPublicacio) {
        this.dataPublicacio = dataPublicacio;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "usuari=" + usuari +
                ", canal=" + canal +
                ", id=" + id +
                ", fil=" + fil +
                ", titol='" + titol + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", etiquetes='" + etiquetes + '\'' +
                ", dataPublicacio=" + dataPublicacio +
                ", imatge='" + imatge + '\'' +
                ", audio='" + audio + '\'' +
                '}';
    }
}
