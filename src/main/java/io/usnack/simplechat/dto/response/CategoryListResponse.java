package io.usnack.simplechat.dto.response;

import java.util.List;
import java.util.UUID;

public record CategoryListResponse(
        List<CategoryDetailResponse> categories
) {
}
