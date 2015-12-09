package com.bitscanvas.thehytt.dtos;

import java.util.List;

/**
 * Created by THEHYTT on 29/08/15.
 */
public class JourneyItemDto {

    List<HopDto> hopDtos;

    List<PostDto> postDtos;

    public List<HopDto> getHopDtos() {
        return hopDtos;
    }

    public void setHopDtos(List<HopDto> hopDtos) {
        this.hopDtos = hopDtos;
    }

    public List<PostDto> getPostDtos() {
        return postDtos;
    }

    public void setPostDtos(List<PostDto> postDtos) {
        this.postDtos = postDtos;
    }

}
