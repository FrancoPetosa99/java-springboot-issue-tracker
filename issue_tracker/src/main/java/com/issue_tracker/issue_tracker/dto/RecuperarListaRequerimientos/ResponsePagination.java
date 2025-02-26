package com.issue_tracker.issue_tracker.dto.RecuperarListaRequerimientos;

import org.springframework.data.domain.Pageable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponsePagination {
    private List<DetalleRequerimiento> listaRequerimientos;
    private Integer pageSize;
    private Integer currentPage;
    private Integer total;
    private String sortBy;
    private String sortOrder;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private Pageable previousPage;
    private Pageable nextPage;
}