package lk.earth.earthuniversity.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = " package_has_invoice", schema = "clippercuts", catalog = "")
public class PackageHasInvoice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "prpice")
    private String prpice;
    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id", nullable = false)
    private Invoice invoice;
    @ManyToOne
    @JoinColumn(name = " Package_id", referencedColumnName = "id", nullable = false)
    private lk.earth.earthuniversity.entity.Package Package;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrpice() {
        return prpice;
    }

    public void setPrpice(String prpice) {
        this.prpice = prpice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageHasInvoice that = (PackageHasInvoice) o;
        return Objects.equals(id, that.id) && Objects.equals(prpice, that.prpice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prpice);
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public lk.earth.earthuniversity.entity.Package getPackage() {
        return Package;
    }

    public void setPackage(lk.earth.earthuniversity.entity.Package aPackage) {
        Package = aPackage;
    }
}
