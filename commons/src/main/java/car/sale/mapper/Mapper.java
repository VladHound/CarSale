package car.sale.mapper;

public interface Mapper<T, R> {
    R map(T t);
}
