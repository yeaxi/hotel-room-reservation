package ua.dudka.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Rostislav Dudka
 */
@RequiredArgsConstructor
@Getter
public enum HotelRoomType {
    ONE_ROOM(1),
    TWO_ROOM(2),
    THREE_ROOM(3),
    LUX(4),;

    private final int roomAmount;
}