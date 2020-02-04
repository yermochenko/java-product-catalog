package org.itstep.util;

/**
 * Класс для хранения списка объектов
 * @param <T> тип объектов для хранения в списке
 * Данный тип используется для ограничения типов тех объектов,
 * которые будут добавлены в массив (ограничение работает на
 * уровне компилятора)
 */
public class List<T> {
	public static final int RESERVE_SIZE = 100;
	private Object buffer[];
	private int size = 0;

	/**
	 * Создание пустого списка с размером 0
	 * @param bufferSize начальный размер буфера
	 */
	public List(int bufferSize) {
		buffer = new Object[bufferSize];
	}

	/**
	 * Создание пустого списка с размером 0 с начальным размером
	 * буфера по умолчанию
	 */
	public List() {
		this(RESERVE_SIZE);
	}

	/**
	 * @return количество реально хранимых объектов в списке
	 */
	public int size() {
		return size;
	}

	/**
	 * получает элемент из списка
	 * @param index индекс получаемого элемента
	 * @return ссылку на элемент, приведённую к типу, которым был
	 * параметризован объект класса List
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if(index < size) {
			return (T)buffer[index];
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * заменяет элемент в списке (размер списка не изменяется)
	 * @param index индекс заменяемого элемента
	 * @param object новый объект, который записывается вместо старого
	 * (имеет тот тип, которым параметризован объект класса List)
	 */
	public void set(int index, T object) {
		if(index < size) {
			buffer[index] = object;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * добавляет новый элемент к концу списка (размер списка
	 * увеличивается на 1)
	 * @param object новый объект (имеет тот тип, которым
	 * параметризован объект класса List)
	 */
	public void add(T object) {
		if(size == buffer.length) {
			Object newBuffer[] = new Object[buffer.length + RESERVE_SIZE];
			System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
			buffer = newBuffer;
		}
		buffer[size++] = object;
	}

	/**
	 * удаляет элемент из списка со сдвигом остальных элементов
	 * @param index индекс удаляемого элемента
	 */
	public void del(int index) {
		if(index < size) {
			for(int i = index; i < size - 1; i++) {
				buffer[i] = buffer[i + 1];
			}
			size--;
			buffer[size] = null;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
}
