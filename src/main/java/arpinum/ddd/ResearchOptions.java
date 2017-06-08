package arpinum.ddd;

import java.util.*;

public class ResearchOptions {

    public Optional<SortOptions> sortOptions = Optional.empty();

    public Optional<Integer> limit = Optional.empty();

    public Optional<Integer> skip = Optional.empty();


    public ResearchOptions withSortOptions(SortOptions options) {
        this.sortOptions = Optional.of(options);
        return this;
    }

    public ResearchOptions withLimit(int limit) {
        this.limit = Optional.of(limit);
        return this;
    }

    public ResearchOptions withSkip(int skip) {
        this.skip = Optional.of(skip);
        return this;
    }
}
