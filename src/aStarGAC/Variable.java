package aStarGAC;

import java.util.List;

/**
 * Created by Kyrre on 03/10/2014.
 */
public abstract class Variable {
    protected long id;
    List<Object> domain;

    public long getId() {
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
}
