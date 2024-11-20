package io.usnack.simplechat.dto.data;

import java.util.List;

public record PageableData<T>(
        List<T> data,
        boolean hasMore,
        int nextPage,
        int size
) {
}
