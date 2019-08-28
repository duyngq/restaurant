package ampos.miniproject.restaurant.model;

public interface DomainEntity<ID> {
    ID getId();

    void setId(ID id);
}
