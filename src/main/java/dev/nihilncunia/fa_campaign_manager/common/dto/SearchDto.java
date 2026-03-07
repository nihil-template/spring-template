package dev.nihilncunia.fa_campaign_manager.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "검색 DTO")
public class SearchDto {
  
  @Builder.Default
  @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
  @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
  private Integer page = 0;
  
  @Min(value = 0, message = "페이지 크기는 0 이상이어야 합니다.")
  @Max(value = 100, message = "페이지 크기는 100을 초과할 수 없습니다.")
  @Schema(description = "페이지당 개수 (null 또는 1 미만일 경우 페이징 없이 전체 조회)", example = "10", nullable = true)
  private Integer size;
  
  @Schema(
    description = "정렬 기준 (형식: 컬럼명,asc|desc). 여러 개 지정 가능",
    example = "[\"userName,asc\", \"createDate,desc\"]"
  )
  private List<String> sort;
  
  @Schema(description = "검색 필드 (검색 대상 컬럼명)", example = "userName")
  private String searchType;
  
  @Schema(description = "검색 키워드 (실제 검색할 문자열)", example = "NIHILncunia")
  private String searchKeyword;
  
  /**
   * 현재 DTO에 설정된 page, size, sort 값을 기반으로 Pageable 객체를 생성합니다.
   * size가 1 미만이면 페이징을 적용하지 않습니다.
   * @return Spring Data JPA의 Pageable 객체
   */
  @JsonIgnore
  @Schema(hidden = true)
  public Pageable getPageable() {
    
    // 정렬 조건을 Spring Sort 객체로 변환
    Sort springSort = this.toSpringSort();
    
    // size가 없거나 1 미만이면 페이징 없이 정렬만 적용
    if (this.size == null || this.size < 1) {
      return Pageable.unpaged(springSort);
    }
    
    // page 값 보정 (null 또는 음수 방지)
    int p = (this.page == null || this.page < 0) ? 0 : this.page;
    
    // PageRequest 생성 (page, size, sort 포함)
    return PageRequest.of(p, this.size, springSort);
  }
  
  
  /**
   * List 형태의 정렬 파라미터를 Spring Data의 Sort 객체로 변환합니다.
   * @return 변환된 Sort 객체
   */
  private Sort toSpringSort() {
    
    // 정렬 조건이 없으면 정렬 미적용
    if (this.sort == null || this.sort.isEmpty()) {
      return Sort.unsorted();
    }
    
    List<Sort.Order> orders = new ArrayList<>();
    
    for (String s : this.sort) {
      if (s == null || s.isBlank()) continue;
      
      // "컬럼명,정렬방향" 형식 파싱
      String[] parts = s.split(",");
      String property = parts[0].trim();
      
      // 기본값은 ASC
      Sort.Direction direction = Sort.Direction.ASC;
      
      // 두 번째 값이 desc이면 내림차순 적용
      if (parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim())) {
        direction = Sort.Direction.DESC;
      }
      
      // 정렬 조건 추가
      orders.add(new Sort.Order(direction, property));
    }
    
    // 유효한 정렬 조건이 없으면 정렬 미적용
    return orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);
  }
  
}
