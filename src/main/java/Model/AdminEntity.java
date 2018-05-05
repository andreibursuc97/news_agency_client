package Model;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "admin", schema = "agentie_presa")
public class AdminEntity {
    private int id;
    private String username;
    private byte[] parola;

    public AdminEntity(String username, byte[] parola) {
        this.username = username;
        this.parola = parola;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "parola")
    public byte[] getParola() {
        return parola;
    }

    public void setParola(byte[] parola) {
        this.parola = parola;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminEntity that = (AdminEntity) o;

        if (id != that.id) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (!Arrays.equals(parola, that.parola)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(parola);
        return result;
    }
}
