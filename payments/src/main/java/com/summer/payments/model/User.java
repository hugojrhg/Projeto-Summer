package com.summer.payments.model;


public class User implements Serializable{

    private static final long serialVersionUID = 1l;
        
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    //private String cep;
    private String numCartão;
    private List<Orders> compras;
        
    public User() {}
    
    public User(Long id, String nome, String cpf,  String email, String numCartão, List<Orders> compras) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.numCartão = numCartão;
        this.compras = compras;
        }
    
    public Long getId() {
        return id;
        }
    
    public String getNome() {
        return nome;
        }
    
    public void setNome(String nome) {
        this.nome = nome;
        }
    
    public String getCpf() {
        return cpf;
        }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
        }
    
    public String getEmail() {
        return email;
        }
    
    public void setEmail(String email) {
        this.email = email;
        }
    
    public String getNumCartão() {
        return numCartão;
        }
    
    public void setNumCartão(String numCartão) {
        this.numCartão = numCartão;
        }
    
    public List<Orders> getCompras() {
        return compras;
        }
    
    public void setCompras(List<Orders> compras) {
        this.compras = compras;
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            User other = (User) obj;
            return Objects.equals(id, other.id);
        }
    
        @Override
        public String toString() {
            return "User [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", numCartão=" + numCartão
                    + ", compras=" + compras + "]";
        }
    
}
