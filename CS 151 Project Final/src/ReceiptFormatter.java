/**
 * Strategy pattern
 * ReceiptFormatter interface requires the classes who implement itself to have a method "format" with Model as parameter.
 * It will return simple or comprehensive receipt using Polymorphism.
 * Model is the context program.
 * @author yitong
 *
 */
public interface ReceiptFormatter {
	String format(Model m);

}


