package com.example.user_management.utility;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {

    private String message;
    private int code;
    private Optional<Meta> meta;
    private Optional<T> data;

    // Constructor without Meta for simple responses
    public ResponseWrapper(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.meta = Optional.empty();
        this.data = Optional.of(data);
    }

    // Constructor with Meta for paginated responses
    public ResponseWrapper(String message, int code, Meta meta, T data) {
        this.message = message;
        this.code = code;
        this.meta = Optional.of(meta);
        this.data = Optional.of(data);
    }

    // Additional constructor for null data cases (like delete operations)
    public ResponseWrapper(String message, int code) {
        this.message = message;
        this.code = code;
        this.meta = Optional.empty();
        this.data = Optional.empty();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {

        private int page;
        private int size;
        private int totalData;
        private int totalPage;
        private Optional<Integer> prevPage;
        private Optional<Integer> nextPage;

        // Static factory method for Builder with Optional fields
        public static MetaBuilder builder() {
            return new MetaBuilder()
                .prevPage(Optional.empty())
                .nextPage(Optional.empty());
        }
    }
}
