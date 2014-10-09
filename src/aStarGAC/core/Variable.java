package aStarGAC.core;

import java.util.List;

/**
 * Created by Kyrre on 03/10/2014.
 */
public abstract class Variable {
    protected int id;
    protected List<?> domain;

    protected Variable(int id, List<?> domain) {
        this.id = id;
        this.domain = domain;
    }

    public int getId() {
        return id;
    }

    public boolean isDomainSingleton() {
        if (domain.size() == 1){
            return true;
        }
        return false;
    }

    public boolean hasEmptyDomain() {
        return domain.isEmpty();
    }

    public int getDomainSize() {
        return domain.size();
    }

    public List<?> getDomain() {
        return domain;
    }

    public abstract Variable deepCopy();

    public  void setDomain(List<?> domain){
        this.domain = domain;
    }
}
