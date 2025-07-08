package lk.earth.earthuniversity.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = " package_has_service", schema = "clippercuts", catalog = "")
public class PackageHasService {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private Service service;
    @ManyToOne
    @JoinColumn(name = " Package_id", referencedColumnName = "id", nullable = false)
    private lk.earth.earthuniversity.entity.Package Package;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageHasService that = (PackageHasService) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public lk.earth.earthuniversity.entity.Package getPackage() {
        return Package;
    }

    public void setPackage(lk.earth.earthuniversity.entity.Package aPackage) {
        Package = aPackage;
    }
}
